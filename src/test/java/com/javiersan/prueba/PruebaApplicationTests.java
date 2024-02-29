package com.javiersan.prueba;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import com.javiersan.prueba.exceptions.NaveNotFoundException;
import com.javiersan.prueba.models.dao.INaveDao;
import com.javiersan.prueba.models.entity.Nave;
import com.javiersan.prueba.models.service.INaveService;

@SpringBootTest
class PruebaApplicationTests {

	@MockBean
	INaveDao navedao;

	@Autowired
	INaveService service;


	@Test
	void contextLoads() {
		
		when(navedao.findAll(Pageable pageRequest)).thenReturn(Page <Nave> ); 
		when(navedao.save(Nave nave)).thenReturn(Nave); 
		when(navedao.findByName(String term)).thenReturn(List<Nave>); 
		when(navedao.findById(Long id)).thenReturn(Nave); 
		when(navedao.delete(Long id)).thenReturn(); 

   
		Page<Nave> resultado = service.findAll(pageRequest);
		assertEquals(Page, resultado.toPlainString()); 
		Nave resultado = service.save(nave);
		assertEquals(Nave, resultado);
		List<Nave> resultado = service.findByName(term);
		assertEquals(List<Nave>, resultado);
		Nave resultado = service.findById(id);
		assertEquals(Nave, resultado);
		String resultado = service.delete(id);
		assertEquals(String, resultado);


		verify(navedao, times(1)).findAll(pageRequest); 
		verify(navedao, times(1)).save(nave); 
		verify(navedao, times(1)).findByName(term); 
		verify(navedao, times(1)).findById(id); 
		verify(navedao, times(1)).delete(id); 

	}


	@Test
	void contextLoads2() {

		
		when(navedao.findAll( pageRequest)).thenReturn(Page <Nave> ); 

		Page<Nave> resultado = service.findAll(pageRequest);
		assertEquals(Page, resultado.toPlainString()); 


		assertThrows(NumberFormatException.class, ()-> {
			service.findAll(new Long("-15")); 
		});

		assertThrows(DataAccessException.class, ()-> {
			service.findAll(new Long("8")); 
		});


	}

	@Test
	void contextLoads3() {

		when(navedao.save( nave )).thenReturn(Nave); 

		
		Nave resultado = service.save(nave);
		assertEquals(Nave, resultado);
		


		assertThrows(NullPointerException.class, ()-> {
			service.save(new Nave("coche")); 
		});

		assertThrows(DataAccessException.class, ()-> {
			service.save(new Nave("nave")); 
		});


	}

	
	@Test
	void contextLoads4() {

		when(navedao.findByName( term)).thenReturn(List<Nave>); 

		
		List<Nave> resultado = service.findByName(term);
		assertEquals(List<Nave>, resultado);
		


		assertThrows(NaveNotFoundException.class, ()-> {
			service.findByName(new String("coche")); 
		});

		assertThrows(DataAccessException.class, ()-> {
			service.findByName(new String("nave")); 
		});


	}

	
	@SuppressWarnings("removal")
	@Test
	void contextLoads5() {

		when(navedao.findById( id)).thenReturn(Nave); 

		
		Nave resultado = service.findById(id);
		assertEquals(Nave, resultado);
		


		assertThrows(NaveNotFoundException.class, ()-> {
			service.findById(new Long(65)); 
		});

		assertThrows(DataAccessException.class, ()-> {
			service.findById(new Long(65)); 
		});

		assertThrows(NumberFormatException.class, ()-> {
			service.findById(new Long(-15)); 
		});


	}

	@SuppressWarnings("removal")
	@Test
	void contextLoads6() {

		when(navedao.delete( id)).thenReturn(); 

		
		String resultado = service.delete(id);
		assertEquals(String, resultado);
		


		assertThrows(NaveNotFoundException.class, ()-> {
			service.delete(new Long(65)); 
		});

		assertThrows(DataAccessException.class, ()-> {
			service.delete(new Long(65)); 
		});

		assertThrows(NumberFormatException.class, ()-> {
			service.delete(new Long(-15)); 
		});


	}






	@Test
	void contextLoads7() {

		when(navedao.findById(12)).thenReturn(nave);


		Nave resultado1 = service.findById((long) 12);
		Nave resultado2 = service.findById((long)12);

		assertSame(resultado1, resultado2);

		assertEquals("Nave principal", resultado1.getNombre());
		assertEquals("Nave principal", resultado2.getNombre());

		verify(navedao, times(2)).findById(12);
	}

}
