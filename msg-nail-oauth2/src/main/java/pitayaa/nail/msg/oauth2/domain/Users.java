package pitayaa.nail.msg.oauth2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Users {
	
	@Id
    @Column( nullable = false, unique=true)
    @Size(min = 0, max = 50)
    private String username;
		
	@Version
	Long version;

    @Size(min = 0, max = 500)
    private String password;

    private boolean activated;

    @Size(min = 0, max = 100)
    @Column(name = "activationkey")
    private String activationKey;

    @Size(min = 0, max = 100)
    @Column(name = "resetpasswordkey")
    private String resetPasswordKey;
      
    private String roles;
    
}
