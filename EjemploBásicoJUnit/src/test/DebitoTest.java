package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import modelo.Debito;
import modelo.Movimiento;
import modelo.Cuenta;

public class DebitoTest {
	private Date fecha;
	private Debito debito;
	private Cuenta unaCuenta;
	
	@BeforeEach
	public void setUp() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = "2022-09-22";
		dateInString = "2022-09-22";
		try {
			fecha = sdf.parse(dateInString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		debito = new Debito("01-123456-78","Gonzalo Cerbelli",fecha);
		unaCuenta=new Cuenta("12-555555-12","Jose Perez");
	}

	@Test
	public void testComprasConTarjetaDebito(){
	
		assertNotNull(fecha);
		
		try {
			unaCuenta.ingresar(2000.00);
			debito.setCuenta(unaCuenta);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			debito.pagoEnEstablecimiento("Coto",500.00);
			debito.pagoEnEstablecimiento("Dia",650.00);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Saldo de la cuenta = " + unaCuenta.getSaldo());
		
		assertTrue(unaCuenta.getSaldo()==850.00);
		
	}
	
	@Test
	public void testIngresarEfectivoConTarjetaDebito(){
		
		assertNotNull(fecha);
		
		try {
			unaCuenta.ingresar(100);
			debito.setCuenta(unaCuenta);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			debito.ingresar(19900);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Saldo de la cuenta = " + unaCuenta.getSaldo());
		
		assertTrue(unaCuenta.getSaldo()==20000.00);
		
	}
	
	@Test
	public void testRetirarEfectivoConTarjetaDebito(){
		
		assertNotNull(fecha);
		
		try {
			unaCuenta.ingresar(20000.00);
			debito.setCuenta(unaCuenta);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			debito.retirar(15000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Saldo de la cuenta = " + unaCuenta.getSaldo());
		
		assertTrue(unaCuenta.getSaldo()==5000.00);
		
	}
	
	@Test
	public void testRetirarEfectivoSinFondosConTarjetaDebito(){
		
		assertNotNull(fecha);
		
		try {
			unaCuenta.ingresar(100.00);
			debito.setCuenta(unaCuenta);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			debito.retirar(15000);
		} catch (Exception e) {
			System.out.println(e);
			assertTrue(e instanceof java.lang.Exception);
			assertTrue(e.getMessage().equals("Saldo insuficiente"));
		}
		
		assertTrue(unaCuenta.getSaldo() == 100);
		
	}
	
	@Test
	public void testMovimientosConTarjetaDebito(){
		
		assertNotNull(fecha);
		
		try {
			unaCuenta.ingresar(10000.00);
			debito.setCuenta(unaCuenta);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			debito.pagoEnEstablecimiento("Coto",500.00);
			debito.pagoEnEstablecimiento("Dia",650.00);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Movimiento> movimientos = unaCuenta.getMovimientos();
		int balance = 0;
		
		for (int i = 0; i < 3; i++) {
			System.out.println(movimientos.get(i).getImporte());
			balance = (int) (balance + movimientos.get(i).getImporte());
		}
		
		assertTrue(balance == unaCuenta.getSaldo());
		
	}

}
