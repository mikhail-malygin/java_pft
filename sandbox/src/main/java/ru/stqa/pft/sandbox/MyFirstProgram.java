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

		Point p1 = new Point(3, 5);
		Point p2 = new Point(2, -1.56);

		/* Расчет расстояния между двумя точками через функцию
		System.out.println("Расстояние между двумя точками p1 и p2 = " + distance(p1, p2));
		*/

		// Расчет расстояния между двумя точками через метод
		System.out.println("Расстояние между двумя точками p1 и p2 = " + p1.distanceMethod(p1, p2));
	}

	public static void hello(String somebody) {
		System.out.println("Hello, " + somebody + "!");
	}

	/* Функция расчета расстояния между двумя точками
	public static double distance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}
	 */
}
