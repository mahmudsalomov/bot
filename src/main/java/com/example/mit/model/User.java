package com.example.mit.model;

import com.example.mit.bot.State;
import com.example.mit.util.Language;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "telegram_user", uniqueConstraints = {@UniqueConstraint(columnNames = "chat_id", name = "users_unique_chatid_idx")})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "chat_id", unique = true, nullable = false)
    @NotNull
    private Integer chatId;

    @Column(name = "name", unique = true, nullable = false)
    @NotBlank
    private String name;

    @Column(name = "score", nullable = false)
    @NotNull
    private Integer score;

    @Column(name = "high_score", nullable = false)
    @NotNull
    private Integer highScore;

    private String language;

    private String phone;

    @Column(name = "bot_state", nullable = false)
    private State botState;

    @Column(name = "current_category_id", nullable = false)
    private Integer current_category_id;
    public User(int chatId) {
        this.chatId = chatId;
        this.name = String.valueOf(chatId);
        this.score = 0;
        this.highScore = 0;
        this.botState = State.START;
        this.current_category_id=1;
    }
}
