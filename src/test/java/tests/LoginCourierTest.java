package tests;

import clients.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.pojo.CourierLogin;
import org.example.pojo.CourierRegister;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class LoginCourierTest {

    CourierClient courierClient;
    String courierId;

    CourierRegister courier = new CourierRegister("Paul767611>", "qwerty111999", "PaulAlex");

    @After
    public void tearDown(){
        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Логин, валидные данные")
    public void loginWithExistingCourier() {
        CourierClient.createNewCourier(courier);

        CourierLogin courierLogin = new CourierLogin(this.courier.getLogin(), this.courier.getPassword());
        Response logInResponse = CourierClient.loginCourier(courierLogin);

        CourierClient.comparingSuccessfulLoginResponseCodeWithActual(logInResponse, 200);

        courierId = CourierClient.getCourierId(logInResponse);

    }

    @Test
    @DisplayName("Логин, поле логин пустое")
    public void loginWithEmptyLogin() {

        CourierClient.createNewCourier(courier);

        CourierLogin courierLogin = new CourierLogin("", this.courier.getLogin());
        Response logInResponse = CourierClient.loginCourier(courierLogin);

        CourierClient.comparingOfTheErroneousResponseCodeWithTheActualOne(logInResponse, 400, "Недостаточно данных для входа");

        CourierLogin courierLogin2 = new CourierLogin(this.courier.getLogin(), this.courier.getPassword());
        Response logInResponse2 = CourierClient.loginCourier(courierLogin2);

        courierId = CourierClient.getCourierId(logInResponse2);

    }

    @Test
    @DisplayName("Логин, логин не введен")
    public void loginWithoutLogin() {

        CourierClient.createNewCourier(courier);

        CourierLogin courierLogin = new CourierLogin(null, this.courier.getLogin());
        Response logInResponse = CourierClient.loginCourier(courierLogin);

        CourierClient.comparingOfTheErroneousResponseCodeWithTheActualOne(logInResponse, 400, "Недостаточно данных для входа");

        CourierLogin courierLogin2 = new CourierLogin(this.courier.getLogin(), this.courier.getPassword());
        Response logInResponse2 = CourierClient.loginCourier(courierLogin2);

        courierId = CourierClient.getCourierId(logInResponse2);

    }

    @Test
    @DisplayName("Логин, пароль не введен")
    public void loginWithEmptyPassword() {

        CourierClient.createNewCourier(courier);

        CourierLogin courierLogin = new CourierLogin(this.courier.getLogin(), "");
        Response logInResponse = CourierClient.loginCourier(courierLogin);

        CourierClient.comparingOfTheErroneousResponseCodeWithTheActualOne(logInResponse, 400, "Недостаточно данных для входа");

        CourierLogin courierLogin2 = new CourierLogin(this.courier.getLogin(), this.courier.getPassword());
        Response logInResponse2 = CourierClient.loginCourier(courierLogin2);

        courierId = CourierClient.getCourierId(logInResponse2);

    }

    @Test
    @DisplayName("Логин, невалидный пароль")
    public void loginWithIncorrectLogin() {

        CourierClient.createNewCourier(courier);

        CourierLogin courierLogin = new CourierLogin("incorrect_login", this.courier.getPassword());
        Response logInResponse = CourierClient.loginCourier(courierLogin);

        CourierClient.comparingOfTheErroneousResponseCodeWithTheActualOne(logInResponse, 404, "Учетная запись не найдена");

        CourierLogin courierLogin2 = new CourierLogin(this.courier.getLogin(), this.courier.getPassword());
        Response logInResponse2 = CourierClient.loginCourier(courierLogin2);

        courierId = CourierClient.getCourierId(logInResponse2);

    }

}
