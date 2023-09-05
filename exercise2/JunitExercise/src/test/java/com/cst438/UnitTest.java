package com.cst438;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UnitTest {
	
	@MockBean
	DeviceReader devReader;
	
	@MockBean
	DeviceOutput devOutput;
	
	InsulinPump ip;
	
	@BeforeEach
	public void setup() {
		ip = new InsulinPump(devReader, devOutput);
	}
	
	@Test
	public void test1() {
		//TO DO 
	}

}
