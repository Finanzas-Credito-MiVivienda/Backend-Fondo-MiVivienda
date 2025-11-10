package pe.edu.upc.backendfinanzas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pe.edu.upc.backendfinanzas.entities.EstadoVivienda;
import pe.edu.upc.backendfinanzas.entities.Inmueble;
import pe.edu.upc.backendfinanzas.entities.TipoVivienda;
import pe.edu.upc.backendfinanzas.repositories.InmuebleRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class BackendFinanzasApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendFinanzasApplication.class, args);
    }

    @Bean
    public CommandLineRunner mappingDemo(
            InmuebleRepository inmuebleRepository

    ) {
        return args -> {
            if (inmuebleRepository.count() == 0) {
                inmuebleRepository.save(new Inmueble(0, BigDecimal.valueOf(117),
                        BigDecimal.valueOf(195114), "Lima", "Icaro 164 Chorrillos",
                        TipoVivienda.TRADICIONAL, EstadoVivienda.CONSTRUIDO, "Lima",
                        "Chorrillos","https://img10.naventcdn.com/avisos/resize/111/01/47/63/69/46/1200x1200/1568424575.jpg?isFirstImage=true",
                        null));

                inmuebleRepository.save(new Inmueble(0, BigDecimal.valueOf(66),
                        BigDecimal.valueOf(519000), "Lima", "Av. Cesar Vallejo 1445, Lince",
                        TipoVivienda.SOSTENIBLE, EstadoVivienda.EN_CONSTRUCCION, "Lima",
                        "Lince","https://img10.naventcdn.com/avisos/resize/111/00/58/80/59/56/1200x1200/1562971826.jpg?isFirstImage=true",
                        null));

                inmuebleRepository.save(new Inmueble(0, BigDecimal.valueOf(97),
                        BigDecimal.valueOf(670000), "Lima", "Av. 28 De Julio 699, San Antonio",
                        TipoVivienda.INTEGRADOR_TRADICIONAL, EstadoVivienda.TERRENO, "Lima",
                        "Miraflores","https://img10.naventcdn.com/avisos/resize/111/01/45/59/03/44/1200x1200/1507996778.jpg",
                        null));

                inmuebleRepository.save(new Inmueble(0, BigDecimal.valueOf(131),
                        BigDecimal.valueOf(243555), "Lima", "Jr. Santa Susana 163 , Pando Et.3",
                        TipoVivienda.INTEGRADOR_SOSTENIBLE, EstadoVivienda.CONSTRUIDO, "Lima",
                        "Cercado de Lima","https://img10.naventcdn.com/avisos/resize/111/01/45/72/97/59/1200x1200/1557175396.jpg?isFirstImage=true",
                        null));

                inmuebleRepository.save(new Inmueble(0, BigDecimal.valueOf(109),
                        BigDecimal.valueOf(210641), "Lima", "Pasaje Los Aquijes 118 Surco, Santiago de Surco",
                        TipoVivienda.TRADICIONAL, EstadoVivienda.EN_CONSTRUCCION, "Lima",
                        "Surco","https://img10.naventcdn.com/avisos/resize/111/01/47/64/21/41/1200x1200/1561248856.jpg?isFirstImage=true",
                        null));

                inmuebleRepository.save(new Inmueble(0, BigDecimal.valueOf(1150),
                        BigDecimal.valueOf(180000), "Lima", "Pasaje Los Aquijes 118 Surco, Santiago de Surco",
                        TipoVivienda.TRADICIONAL, EstadoVivienda.EN_CONSTRUCCION, "Lima",
                        "Surco","https://img10.naventcdn.com/avisos/resize/111/01/47/64/21/41/1200x1200/1561248856.jpg?isFirstImage=true",
                        null));
            }
        };
    }
}