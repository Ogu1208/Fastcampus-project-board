package com.zerobase.zerobaseprojectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "userId"),
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class UserAccount extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 50)
    private String userId; // userId
    @Setter
    @Column(nullable = false)
    private String userPassword; // 비밀번호

    @Setter
    @Column(length = 100)
    private String email; // 이메일
    @Setter
    @Column(length = 100)
    private String nickname; // 닉네임
    @Setter
    private String memo; // 제목

    protected UserAccount() {}

    public UserAccount(String userId, String userPassword, String email, String nickname, String memo) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo) {
        return new UserAccount(userId, userPassword, email, nickname, memo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount userAccount)) return false;
        return id != null && id.equals(userAccount.id);
        // id를 아직 부여받지 않았을 때 - entity만 있고 영속화를 하지 않았을 때(db에 insert되기 전)
        // 막 만든 아직 영속화 되지 않은 entity는 동등성 검사에서 탈락됨.(다른값으로 취급)
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
