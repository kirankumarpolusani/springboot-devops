package com.sapient.feecalc.util;

import com.sapient.feecalc.dto.Employee;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Lambda1 {
    public static void main(String[] args) {


        Comparator<Employee> byName = new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };

        Comparator<Employee> byNameLambda1 = (Employee o1, Employee o2) -> { return o1.getName().compareTo(o2.getName()); };

        Comparator<Employee> byNameLambda2 = (a, b) -> { return a.getName().compareTo(b.getName()); };

        Comparator<Employee> byNameLambda3 = (a, b) -> a.getName().compareTo(b.getName());

        Thread t1 = new Thread(() -> {
            System.out.println("In Contructor");
        });

        String[] arr = {"one", "two", "three"};
        System.out.println(getFirst(arr));

        Consumer<String> c = s -> System.out.println(s.length());
        c.accept("ACVFD");

        Consumer<?> c1 = (String s) -> System.out.println(s.length());

        Supplier<Thread> s1 = Thread::currentThread;
        Thread t = s1.get();
        ThreadSupplier ts = Thread::currentThread;
        ts.giveMeAThread();

        Comparator<Employee> byName1 = Comparator.comparing(Employee::getName);

        Employee mike = new Employee("mike", 2000);
        Employee frank = new Employee("frank", 3000);

        Supplier<Integer> s2 = frank::getSalary;
        System.out.println(s2.get());

        Function<Employee, Integer> f1 = Employee::getSalary;
        System.out.println(f1.apply(mike));

        printAll(getDept(), Employee::getName);

        printAll(getDept(), emp -> ""+emp.getSalary());

    }

    public static Employee[] getDept() {
        Employee[] dept = new Employee[5];
        dept[0] = new Employee("mike", 2000);
        dept[1] = new Employee("clarke", 1500);
        dept[2] = new Employee("john", 1200);
        dept[3] = new Employee("smith", 2150);
        dept[4] = new Employee("peter", 2300);
        dept[2] = new Employee("Simon", 4500);
        dept[3] = new Employee("Lynda", 2750);
        dept[4] = new Employee("peter", 2300);
        return dept;
    }

    public static <T> void printAll(T[] array, Function<T, String> toStringFun) {
        int i = 0;
        for(T t:array)
            System.out.println(i++ +":\t"+toStringFun.apply(t));
    }

    public static <T> T getFirst(T[] array) {
        return array[0];
    }
}

interface ThreadSupplier {
    Thread giveMeAThread();
}
