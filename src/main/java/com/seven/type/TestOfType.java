package com.seven.type;

import lombok.SneakyThrows;
import org.junit.Test;

import java.lang.reflect.*;
import java.util.*;

public class TestOfType {
    @SneakyThrows
    @Test
    public void testTypeVariable() {
        //****************************TypeVariable************************
        Type type = BlackDog.class.getGenericSuperclass();
        System.out.println(type);
        System.out.println(type.getTypeName());
        if (type instanceof ParameterizedType) {
            ParameterizedType type1 = (ParameterizedType) type;
            System.out.println(type1.getRawType());
            System.out.println(type1.getActualTypeArguments());
        }

        // Field v = TypeTest.class.getField("v");//用反射的方式获取属性 public V v;
        // TypeVariable typeVariable = (TypeVariable) v.getGenericType();//获取属性类型
        // System.out.println("TypeVariable1:" + typeVariable); // V
        // System.out.println("TypeVariable2:" + Arrays.asList(typeVariable.getBounds()));//获取类型变量上界 [class java.lang.Number, interface java.io.Serializable]
        // System.out.println("TypeVariable3:" + typeVariable.getGenericDeclaration());//获取类型变量声明载体 class com.seven.type.TypeTest
        // //1.8 AnnotatedType: 如果这个这个泛型参数类型的上界用注解标记了，我们可以通过它拿到相应的注解
        // AnnotatedType[] annotatedTypes = typeVariable.getAnnotatedBounds();
        // System.out.println("TypeVariable4:" + Arrays.asList(annotatedTypes) + " : " +
        //         Arrays.asList(annotatedTypes[0].getAnnotations())); //[sun.reflect.annotation.AnnotatedTypeFactory$AnnotatedTypeBaseImpl@38cccef, sun.reflect.annotation.AnnotatedTypeFactory$AnnotatedTypeBaseImpl@5679c6c6] : [@com.seven.type.Custom()]
        // System.out.println("TypeVariable5:" + typeVariable.getName()); // V
    }

    /**
     * 带参数的类型
     */
    @SneakyThrows
    @Test
    public void testParameterizedType() {
        //*********************************ParameterizedType**********************************************
        Field list = TypeTest.class.getField("list"); // public List<T> list = new ArrayList<>();
        Type genericType1 = list.getGenericType();
        System.out.println("参数类型1:" + genericType1.getTypeName()); //参数类型1:java.util.List<T>

        Field map = TypeTest.class.getField("map"); // public Map<String, T> map = new HashMap<>();
        Type genericType2 = map.getGenericType();
        System.out.println("参数类型2:" + genericType2.getTypeName());//参数类型2:java.util.Map<java.lang.String, T>

        if (genericType2 instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) genericType2;
            Type[] types = pType.getActualTypeArguments();
            System.out.println("参数类型列表:" + Arrays.asList(types));//参数类型列表:[class java.lang.String, T]
            System.out.println("参数原始类型:" + pType.getRawType());//参数原始类型:interface java.util.Map
            System.out.println("参数父类类型:" + pType.getOwnerType());//参数父类类型:null,因为Map没有外部类，所以为null
        }
    }

    /**
     * 数组类型
     */
    @SneakyThrows
    @Test
    public void testGenericArrayType() {
        //**********************GenericArrayType*********************
        Field tArray = TypeTest.class.getField("tArray");//public T[] tArray;
        System.out.println("数组参数类型1:" + tArray.getGenericType());
        Field ltArray = TypeTest.class.getField("ltArray");// public List<T>[] ltArray;
        System.out.println("数组参数类型2:" + ltArray.getGenericType());//数组参数类型2:java.util.List<T>[]
        if (tArray.getGenericType() instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType) tArray.getGenericType();
            System.out.println("数组参数类型3:" + arrayType.getGenericComponentType());//数组参数类型3:T
        }
        if (ltArray.getGenericType() instanceof GenericArrayType) {
            GenericArrayType genericType = (GenericArrayType) ltArray.getGenericType();
            Type type = genericType.getGenericComponentType();
            System.out.println(type); //java.util.List<T>
            if (type instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) type;
                System.out.println("数组参数类型4:" + Arrays.asList(pType.getActualTypeArguments()));//数组参数类型4:[T]
            }
        }
    }

    @SneakyThrows
    @Test
    public void testWildcardType() {
        //***************************WildcardType*********************************
        Field mapWithWildcard = TypeTest.class.getField("mapWithWildcard"); // public Map<? super String, ? extends Number> mapWithWildcard;
        Type wild = mapWithWildcard.getGenericType();//先获取属性的泛型类型 Map<? super String, ? extends Number>
        if (wild instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) wild;
            Type[] actualTypes = pType.getActualTypeArguments();//获取<>里面的参数变量 ? super String, ? extends Number
            System.out.println("WildcardType1:" + Arrays.asList(actualTypes));
            WildcardType first = (WildcardType) actualTypes[0];//? super java.lang.String
            WildcardType second = (WildcardType) actualTypes[1];//? extends java.lang.Number
            System.out.println("WildcardType2: lower:" + Arrays.asList(first.getLowerBounds()) + "  upper:" + Arrays.asList(first.getUpperBounds()));//WildcardType2: lower:[class java.lang.String]  upper:[class java.lang.Object]
            System.out.println("WildcardType3: lower:" + Arrays.asList(second.getLowerBounds()) + "  upper:" + Arrays.asList(second.getUpperBounds()));//WildcardType3: lower:[]  upper:[class java.lang.Number]
        }
    }

    @SneakyThrows
    @Test
    public void testClass() {
        //**********************************Class*********************************
        Field tClass = TypeTest.class.getField("testClass");
        System.out.println("Class1:" + tClass.getGenericType());//获取泛型类型，由于我们这个属性声明时候没有使用泛型，所以会获得原始类型
        Field tClass2 = TypeTest.class.getField("testClass2");
        System.out.println("Class2:" + tClass2.getGenericType());//获取泛型类型
    }
}
