package org.readers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class DataWriterApp {
    @PostMapping("/writeInstances")
    public String writeInstances(@RequestParam Optional<String> numOfInstances) {
        int numberOfInstances = Integer.parseInt(numOfInstances.orElseGet(() -> "5"));
        long totalTime = DataWriterController.handleWrites(numberOfInstances);
        return "Write has finished, took: " + totalTime + " millis"
                + ", number of instances written: " + numberOfInstances;
    }
}
