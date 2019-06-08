package edu.uni;

        import org.mybatis.spring.annotation.MapperScan;
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.transaction.annotation.EnableTransactionManagement;
        import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan({"edu.uni.*.mapper", "edu.uni.place.mapper"})
@EnableSwagger2
@EnableTransactionManagement
public class UniApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniApplication.class, args);
    }

}
