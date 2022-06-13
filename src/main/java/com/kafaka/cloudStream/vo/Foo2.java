package com.kafaka.cloudStream.vo;

/**
 *
 * author         : AD01701301
 * date           : 2022-06-13
 */
public class Foo2 {

	private String foo;

	public Foo2() {
	}

	public Foo2(String foo) {
		this.foo = foo;
	}

	public String getFoo() {
		return this.foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	@Override
	public String toString() {
		return "FooFail [foo=" + this.foo + "]";
	}
}
