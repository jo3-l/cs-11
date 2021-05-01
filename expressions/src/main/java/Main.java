public class Main {
    public static void main(String[] args) {
        // Expression 1
        System.out.println((9 - 4) + 10 / 2 - (4 - 8) / 3);
        System.out.println(5 + 10 / 2 - (4 - 8) / 3);
        System.out.println(5 + 10 / 2 - (-4) / 3);
        System.out.println(5 + 5 - (-4) / 3);
        // When we divide two integers in Java, the fractional part is discarded. Thus, -4 / 3 becomes -1.
        System.out.println(5 + 5 - (-1));
        System.out.println(10 - (-1));
        System.out.println(11);

        // Expression 2
        System.out.println(23 % 4 + (18 - 3 / 2));
        // Same thing here - the fractional part is discarded.
        System.out.println(23 % 4 + (18 - 1));
        System.out.println(23 % 4 + 17);
        System.out.println(3 + 17);
        System.out.println(20);

        // Expression 3
        System.out.println((8 - 5) + Math.pow(3, 4) / 5);
        System.out.println(3 + Math.pow(3, 4) / 5);
        // Looking at `Math.pow()`'s method signature, we can see that it returns a double rather than an int.
        // Thus, we append a 'd' to the end of the number literal.
        System.out.println(3 + 81d / 5);
        System.out.println(3 + 16.2d);
        System.out.println(19.2d);

        // Expression 4
        System.out.println((9 % 4) - 8 + Math.pow(2, 3) / 8 + (Math.sqrt(81) / 3));
        System.out.println(1 - 8 + Math.pow(2, 3) / 8 + (Math.sqrt(81) / 3));
        System.out.println(1 - 8 + 8d / 8 + (Math.sqrt(81) / 3));
        System.out.println(1 - 8 + 8d / 8 + (9d / 3));
        System.out.println(1 - 8 + 8d / 8 + 3d);
        System.out.println(1 - 8 + 1d + 3d);
        System.out.println(-7 + 1d + 3d);
        System.out.println(-7 + 4d);
        System.out.println(-3d);

        // Expression 5
        System.out.println(((14 / 2 + 3) * Math.sin(45) + 2) - Math.pow(Math.sqrt(25), 3));
        System.out.println(((7 + 3) * Math.sin(45) + 2) - Math.pow(Math.sqrt(25), 3));
        System.out.println((10 * Math.sin(45) + 2) - Math.pow(Math.sqrt(25), 3));
        // We lose a few decimal digits here, which is because our calculation of sin(45rad) is less accurate than that
        // of the computer.
        System.out.println((10 * 0.85090352453d + 2) - Math.pow(Math.sqrt(25), 3));
        System.out.println((10 * 0.85090352453d + 2) - Math.pow(5d, 3));
        System.out.println((10 * 0.85090352453d + 2) - 125d);
        System.out.println((8.5090352453d + 2) - 125d);
        System.out.println(10.5090352453d - 125d);
        System.out.println(-114.4909647547d);
    }
}
