package com.esardor.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// REQUIRED: enable @Mock, @InjectMocks, @Captor
@ExtendWith(MockitoExtension.class)
@DisplayName("CarService Test")
class CarServiceTest {

    // @Mock creates A FAKE UserDao - no real file or DB is touched
    @Mock
    CarDao carDao;

    // @InjectMocks creates UserService and passes our fake UserDao into its constructor
    @InjectMocks
    CarService carService;

    private Car byd017;
    private Car byd777;
    private Car bmw001;
    private Car toyota7001;
    private Car audi1000;
    private List<Car> allCars;
    private List<Car> electricCars;

    @BeforeEach
    void setUp() {
        byd017 = new Car("017", new BigDecimal("89.00"), Brand.BYD, true);
        byd777 = new Car("777", new BigDecimal("125.00"), Brand.BYD, true);
        bmw001 = new Car("001", new BigDecimal("120.00"), Brand.BWM);
        toyota7001 = new Car("7001", new BigDecimal("100.00"), Brand.TOYOTA);
        audi1000 = new Car("1000", new BigDecimal("55.00"), Brand.AUDI);

        allCars = Stream.of(byd017, byd777, bmw001, toyota7001, audi1000).toList();
        electricCars = Stream.of(byd017, byd777).toList();
    }

    //    --- GetCars() Tests ------------------------------------------------------------------------------
    @Nested
    @DisplayName("GetCars() Tests")
    class GetCars {

        @Test
        @DisplayName("Can return all cars")
        void canReturnAllCars() {
            // given
            when(carDao.getCars()).thenReturn(allCars);

            // when
            List<Car> result = carService.getCars();

            // then
            assertNotNull(result);
            assertEquals(5, result.size());
            assertEquals(allCars, result);

            verify(carDao).getCars();
        }

        @Test
        @DisplayName("Can return empty list when no car exists")
        void canReturnEmptyListWhenNoCarExists() {
            // given
            when(carDao.getCars()).thenReturn(Collections.emptyList());

            // when
            List<Car> result = carService.getCars();

            // then
            assertNotNull(result);
            assertEquals(0, result.size());
            assertTrue(result.isEmpty());

            verify(carDao).getCars();
        }

    }

    //    --- GetCars() Tests ------------------------------------------------------------------------------
    @Nested
    @DisplayName("GetElectricCars() Tests")
    class GetElectricCars {

        @Test
        @DisplayName("Can return electric cars")
        void canReturnElectricCars() {
            // given
            when(carDao.getCars()).thenReturn(allCars);

            // when
            List<Car> result = carService.getElectricCar();

            // then
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(electricCars, result);

            verify(carDao).getCars();
        }

        @Test
        @DisplayName("Can return empty list when no electric car exists")
        void canReturnEmptyListWhenNoElectricCarExists() {
            // given
            when(carDao.getCars()).thenReturn(Collections.emptyList());

            // when
            List<Car> result = carService.getElectricCar();

            // then
            assertNotNull(result);
            assertEquals(0, result.size());
            assertTrue(result.isEmpty());

            verify(carDao).getCars();
        }

    }

    //    --- GetCarByRegNumber() Tests --------------------------------------------------------------------------------
    @Nested
    @DisplayName("GetCarByRegNumber() Tests")
    class GetCarByRegNumber {

        @Test
        @DisplayName("Can return car by reg number")
        void canReturnCarByRegNumber() {
            // given
            String regNumber = byd017.getRegNumber();
            when(carDao.getCars()).thenReturn(allCars);
            // when
            Car result = carService.getCarByRegNumber(regNumber);
            // then
            assertNotNull(result);
            assertEquals(regNumber, result.getRegNumber());
            assertEquals(byd017, result);
        }

        @Test
        @DisplayName("Can return null when no car exists by reg number")
        void canReturnNullWhenNoCarExistsByRegNumber() {
            // given
            Car bmw003 = new Car("003", new BigDecimal("323.00"), Brand.BWM);
            when(carDao.getCars()).thenReturn(allCars);
            // when
            Car result = carService.getCarByRegNumber(bmw003.getRegNumber());
            // then
            assertNull(result);
        }
    }

}