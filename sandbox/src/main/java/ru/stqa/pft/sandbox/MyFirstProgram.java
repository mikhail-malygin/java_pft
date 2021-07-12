package ru.stqa.pft.sandbox;

public class MyFirstProgram {

	public static void main(String[] args) {
		hello ("world");
		hello ("user");
		hello ("Mikhail");

		Square square = new Square(5);
		System.out.println("Площадь квадрата со стороной " + square.l + " = " + square.area());

		Rectangle rectangle = new Rectangle(5, 3.5);
		System.out.println("Площадь прямоугольника со сторонами " + rectangle.a + " и " + rectangle.b + " = "
							+ rectangle.area());

	}

	public static void hello(String somebody) {
		System.out.println("Hello, " + somebody + "!");
	}

}
