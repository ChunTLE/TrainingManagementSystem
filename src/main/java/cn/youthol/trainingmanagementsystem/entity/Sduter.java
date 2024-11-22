package cn.youthol.trainingmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Sduter {
    private Integer id;
    private String sdut_id;
    private String password;
    private String name;
    private boolean firstLogin;
}
