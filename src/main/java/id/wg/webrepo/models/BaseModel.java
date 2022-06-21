package id.wg.webrepo.models;

import id.wg.webrepo.security.UserSessionUtil;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseModel {
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;


    @PrePersist
    public void prePersist() {
        try {
            if (UserSessionUtil.isAuthenticated()) {
                this.createdBy = UserSessionUtil.getUsername();
                this.updatedBy = UserSessionUtil.getUsername();
            }
        }catch(Exception e){
            this.createdBy = null;
            this.updatedBy = null;
        }
        this.createdAt = new Timestamp(new Date().getTime());
        this.updatedAt =  new Timestamp(new Date().getTime());
    }

    @PreUpdate
    public void preUpdate() {
        try {
            if (UserSessionUtil.isAuthenticated()) {
                this.updatedBy = UserSessionUtil.getUsername();
            }
        }catch(Exception e){
            this.updatedBy = null;
        }
        this.updatedAt =  new Timestamp(new Date().getTime());
    }
}
