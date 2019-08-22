package com.sapient.feecalc.util;

import com.sapient.feecalc.dto.Employee;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Compose {
    public static void main(String[] args) {
        Function<Employee, String> getName = Employee::getName;
        Function<String, Character> getFirstLetter = name -> name.charAt(0);
        Function<Employee, Character> initial = getName.andThen(getFirstLetter);

        Employee mike = new Employee("M John", 3000);

        //System.out.println(initial.apply(mike));


        Comparator<Employee> byName = Comparator.comparing(Employee::getName);
        Comparator<Employee> bySal = Comparator.comparingInt(Employee::getSalary);

        Comparator<Employee> employeeComparator = byName.thenComparing(bySal);

        List<Employee> emps = Arrays.asList(Lambda1.getDept());
        emps.stream().filter(e -> e.getSalary() > 1500).map(Employee::getName).sorted().forEach(System.out::println);
        System.out.println("************************************");

       /* emps.forEach(e -> System.out.println(e.getName()+ " : "+e.getSalary()));
        System.out.println("************************************");
        Collections.sort(emps, employeeComparator);
        emps.forEach(e -> System.out.println(e.getName()+ " : "+e.getSalary()));
        System.out.println("************************************");
        emps.forEach(System.out::println);


       final Random random = new Random();
        Supplier<Integer> supp = () -> {
            Integer result = random.nextInt();
            System.out.println("Supplying : "+result);
            return result;
        };

        System.out.println("***********************TEST!***********************");
        Stream<Integer> randoms = Stream.generate(supp);
        System.out.println("First Stream built");
        randoms.filter(i -> i>=0).limit(3).forEach(System.out::println);


        System.out.println("***********************TEST2***********************");
        Stream<Integer> randoms2 = Stream.generate(supp);
        randoms2.limit(3).filter(i -> i>=0).forEach(System.out::println);*/


    }


}
