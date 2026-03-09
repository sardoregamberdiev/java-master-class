package com.esardor.booking;

import com.esardor.car.Brand;
import com.esardor.car.Car;
import com.esardor.car.CarService;
import com.esardor.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CarBookingService Test")
class CarBookingServiceTest {

    // ── Two mocks: CarBookingDao and CarService ──────────────────────────────
    @Mock
    CarBookingDao carBookingDao;

    @Mock
    CarService carService;

    // @InjectMocks injects BOTH mocks into the CarBookingService constructor
    @InjectMocks
    CarBookingService carBookingService;

    // ── @Captor: for capturing arguments passed to mocks ────────────────────
    @Captor
    ArgumentCaptor<CarBooking> bookingCaptor;

    private User james;
    private User andre;
    private User nancy;
    private Car byd017;
    private Car byd777;
    private Car bmw001;
    private Car toyota7001;
    private Car audi1000;
    private List<Car> allCars;
    private List<Car> electricCars;
    private List<CarBooking> carBookings;

    @BeforeEach
    void setUp() {
        james = new User(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "James");
        andre = new User(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"), "Andre");
        nancy = new User(UUID.fromString("6ba7b810-9dad-11d1-80b4-00c04fd430c8"), "Nancy");
        byd017 = new Car("017", new BigDecimal("89.00"), Brand.BYD, true);
        byd777 = new Car("777", new BigDecimal("125.00"), Brand.BYD, true);
        bmw001 = new Car("001", new BigDecimal("120.00"), Brand.BWM, false);
        toyota7001 = new Car("7001", new BigDecimal("100.00"), Brand.TOYOTA, false);
        audi1000 = new Car("1000", new BigDecimal("55.00"), Brand.AUDI, false);
        allCars = List.of(byd017, byd777, bmw001, toyota7001, audi1000);
        electricCars = List.of(byd017, byd777);
        carBookings = new ArrayList<>() {{
            add(new CarBooking(UUID.randomUUID(), andre, byd017, LocalDateTime.now(), false));
            add(new CarBooking(UUID.randomUUID(), nancy, bmw001, LocalDateTime.now(), false));
            add(new CarBooking(UUID.randomUUID(), nancy, audi1000, LocalDateTime.now(), false));
        }};
    }

    @Nested
    @DisplayName("SaveBookingCar() Tests")
    class SaveBookingCar {

        @Test
        @DisplayName("Save Car Booking")
        void canSaveBookingCar() {
            // given: no existing bookings → all cars available
            when(carService.getCars()).thenReturn(allCars);
            when(carBookingDao.getCarBookings()).thenReturn(Collections.emptyList());
            when(carService.getCarByRegNumber("017")).thenReturn(byd017);
            doNothing().when(carBookingDao).saveBooking(any(CarBooking.class));

            // when
            UUID bookingId = carBookingService.saveBooking(james, "017");

            // then - 1. check the returned UUID
            assertNotNull(bookingId);
            assertThat(bookingId, notNullValue());
            // UUID should be well-formed (36 chars with dashes)
            assertThat(bookingId.toString(), matchesPattern("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"));

            // then — 2: check the saved booking data via ArgumentCaptor
            verify(carBookingDao).saveBooking(bookingCaptor.capture());
            CarBooking savedBooking = bookingCaptor.getValue();

            assertAll("Saved booked fields",
                    () -> assertNotNull(savedBooking.getBookingId()),
                    () -> assertEquals(bookingId, savedBooking.getBookingId()),
                    () -> assertEquals(james, savedBooking.getUser()),
                    () -> assertEquals(byd017, savedBooking.getCar()),
                    () -> assertNotNull(savedBooking.getBookingTime()),
                    () -> assertFalse(savedBooking.isCancelled())
            );

            assertThat(savedBooking.getUser().getName(), is("James"));
            assertThat(savedBooking.getCar().getRegNumber(), is("017"));
            assertThat(savedBooking.getCar().isElectric(), is(true));

            // then - 3: check the call order
            InOrder inOrder = inOrder(carService, carBookingDao);
            inOrder.verify(carService, atLeastOnce()).getCars();
            inOrder.verify(carService).getCarByRegNumber("017");
            inOrder.verify(carBookingDao).saveBooking(any(CarBooking.class));
        }

    }

    @Nested
    @DisplayName("BookingCarError() Tests")
    class BookingCarError {

        @Test
        @DisplayName("Should throw IllegalStateException when no cars available at all")
        void shouldThrowExceptionWhenNoAvailableCars() {
            // given: no cars at all
            when(carService.getCars()).thenReturn(Collections.emptyList());

            // when
            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> carBookingService.saveBooking(andre, "777")
            );

            // then
            assertThat(exception.getMessage(), containsString("No cars available"));

            // bookingCar must NEVER be called if no cars are available
            verify(carBookingDao, never()).saveBooking(any());
        }

        @Test
        @DisplayName("Should throw IllegalStateException when requested car is already booked")
        void shouldThrowExceptionWhenRequestedCarBooked() {
            // given
            CarBooking existingCarBooking = new CarBooking(UUID.randomUUID(), nancy, bmw001, LocalDateTime.now(), false);
            when(carService.getCars()).thenReturn(allCars);
            when(carBookingDao.getCarBookings()).thenReturn(List.of(existingCarBooking));

            // when
            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> carBookingService.saveBooking(nancy, "001")
            );

            // then
            assertThat(exception.getMessage(), containsString("001"));
            assertThat(exception.getMessage(), containsString("already booked"));

            // verify saveBooking was never called
            verify(carBookingDao, never()).saveBooking(any());

            // Verify carService.getCarByRegNumber was never called (saveBooking failed before it)
            verify(carService, never()).getCarByRegNumber(anyString());
        }

        @Test
        @DisplayName("Should not interact with carService.getCarByRegNumber when no cars available")
        void shouldNotFetchCarDetailsWhenNoCars() {
            // given
            when(carService.getCars()).thenReturn(Collections.emptyList());

            // when
            assertThrows(
                    IllegalStateException.class,
                    () -> carBookingService.saveBooking(james, "777")
            );

            // then
            // Strict verification: getCarByRegNumber was never called
            verify(carService, never()).getCarByRegNumber(anyString());
            verify(carBookingDao, never()).saveBooking(any());
        }
    }

    @Nested
    @DisplayName("GetCarBookings() Tests")
    class GetCarBookings {

        @Test
        @DisplayName("can Return all booked cars")
        void canReturnAllBookedCars() {
            // given
            when(carBookingDao.getCarBookings()).thenReturn(carBookings);

            // when
            List<CarBooking> result = carBookingService.getCarBookings();

            // then
            assertNotNull(result);
            assertEquals(3, result.size());
            assertThat(result, hasItem(carBookings.getFirst()));

            verify(carBookingDao).getCarBookings();
            // carService must NOT be touched just for fetching bookings
            verifyNoInteractions(carService);
        }

        @Test
        @DisplayName("Can return empty list when no car booking exists")
        void canReturnEmptyListWhenNoCarBookingExists() {
            // then
            when(carBookingDao.getCarBookings()).thenReturn(Collections.emptyList());

            // when
            List<CarBooking> result = carBookingService.getCarBookings();

            // then
            assertThat(result, empty());
            assertThat(result, hasSize(0));
        }
    }

    @Nested
    @DisplayName("GetCarBookingsByUser() Tests")
    class GetCarBookingsByUser {

        @Test
        @DisplayName("Get return booked cars by user")
        void canReturnCarBookingsByUser() {
            // given
            when(carBookingDao.getCarBookings()).thenReturn(carBookings);

            // when
            List<CarBooking> result = carBookingService.getCarBookingsByUser(nancy.getId());

            // then
            assertNotNull(result);
            assertThat(result, hasSize(2));
            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("Can return empty list when user has no booking car")
        void canReturnEmptyListWhenUserHasNoBookingCar() {
            // then
            when(carBookingDao.getCarBookings()).thenReturn(carBookings);

            // when
            var result = carBookingService.getCarBookingsByUser(james.getId());

            // then
            assertNotNull(result);
            assertThat(result, hasSize(0));
            assertThat(result, empty());
        }

    }

    @Nested
    @DisplayName("GetAvailableCars() Tests")
    class GetAvailableCars {

        @Test
        @DisplayName("Can return all cars when none are booked")
        void canReturnAvailableCars() {
            // given
            when(carService.getCars()).thenReturn(allCars);
            when(carBookingDao.getCarBookings()).thenReturn(Collections.emptyList());

            // when
            List<Car> result = carBookingService.getAvailableCars();

            // then
            assertNotNull(result);
            assertThat(result, hasSize(5));
            assertThat(result, hasItems(byd017, bmw001, toyota7001));
        }

        @Test
        @DisplayName("Can return available cars from booked cars")
        void canReturnAvailableCarsFromBookedCars() {
            // given
            when(carService.getCars()).thenReturn(allCars);
            when(carBookingDao.getCarBookings()).thenReturn(carBookings);

            // when
            List<Car> result = carBookingService.getAvailableCars();

            // then
            assertNotNull(result);
            assertThat(result, hasSize(2));
            assertThat(result, hasItems(byd777, toyota7001));
            assertThat(result, not(hasItem(byd017)));
            assertThat(result, hasItems(toyota7001));
        }

        @Test
        @DisplayName("Can return empty list when all cars are booked")
        void canReturnEmptyListWhenAllCarsBooked() {
            // given
            carBookings.addAll(List.of(
                    new CarBooking(UUID.randomUUID(), james, byd777, LocalDateTime.now(), false),
                    new CarBooking(UUID.randomUUID(), nancy, toyota7001, LocalDateTime.now(), false)
            ));
            when(carService.getCars()).thenReturn(allCars);
            when(carBookingDao.getCarBookings()).thenReturn(carBookings);

            // when
            List<Car> result = carBookingService.getAvailableCars();

            // then
            assertThat(result, empty());
        }

    }

    @Nested
    @DisplayName("GetAvailableElectricCars() Tests")
    class GetAvailableElectricCars {

        @Test
        @DisplayName("can return available electric cars when none are booked")
        void canReturnAvailableElectricCarsWhenNoneBooked() {
            // given
            when(carService.getElectricCar()).thenReturn(electricCars);
            when(carBookingDao.getCarBookings()).thenReturn(Collections.emptyList());

            // when
            List<Car> result = carBookingService.getAvailableElectricCars();

            // then
            assertNotNull(result);
            assertThat(result, hasSize(2));
        }

        @Test
        @DisplayName("can return available electric cars from booked cars")
        void canReturnAvailableElectricCarsFromBookedCars() {
            // given
            when(carService.getElectricCar()).thenReturn(electricCars);
            when(carBookingDao.getCarBookings()).thenReturn(carBookings);

            // when
            List<Car> result = carBookingService.getAvailableElectricCars();

            // then
            assertNotNull(result);
            assertThat(result, hasSize(1));
            assertThat(result, hasItem(byd777));
            assertThat(result, not(hasItem(byd017)));

        }

        @Test
        @DisplayName("can return empty list when all cars are booked")
        void canReturnEmptyListWhenAllCarsBooked() {
            // given
            carBookings.add(new CarBooking(UUID.randomUUID(), james, byd777, LocalDateTime.now(), false));
            when(carService.getElectricCar()).thenReturn(electricCars);
            when(carBookingDao.getCarBookings()).thenReturn(carBookings);

            // when
            List<Car> result = carBookingService.getAvailableElectricCars();

            // then
            assertNotNull(result);
            assertThat(result, empty());

        }

    }
}