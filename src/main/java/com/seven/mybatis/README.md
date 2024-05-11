@MappedTypes 和 @MappedJdbcTypes 不需要添加，除非有特定的转换

泛型类型转换器 不能放在 类型转换器扫描包下，会报找不到构造函数<init>错误

mybatis 执行到sqlSession.close()时报空指针，可能是因为sqlSession为null导致的，加上判断