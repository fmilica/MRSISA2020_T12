package mrs.isa.team12.clinical.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// Main function
@SpringBootApplication
// podrska za asinhrone zadatke
@EnableAsync
// podrska za transakcije
@EnableTransactionManagement
@EnableJpaRepositories
// podrska za vremensko zakazivanje metoda
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
       
	}

}