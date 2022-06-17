package com.kafaka.cloudStream.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * author         : AD01701301
 * date           : 2022-06-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Foo {

	private String foo;

	@Override
	public String toString() {
		return "Foo [foo=" + this.foo + "]";
	}
}
