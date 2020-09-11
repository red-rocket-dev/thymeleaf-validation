package pl.red.rocket.validation.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;

public class RegistrationForm {
    @NotEmpty
    @Size(min = 2, max = 30)
    private String name;
    @Min(10_000)
    @NotNull
    private BigInteger salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getSalary() {
        return salary;
    }

    public void setSalary(BigInteger salary) {
        this.salary = salary;
    }
}
