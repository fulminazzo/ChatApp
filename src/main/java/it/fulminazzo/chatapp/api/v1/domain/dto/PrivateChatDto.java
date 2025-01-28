package it.fulminazzo.chatapp.api.v1.domain.dto;

import it.fulminazzo.chatapp.api.v1.exceptions.HttpException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivateChatDto {

    private UUID id;

    private UserDto first;

    private UserDto second;

    /**
     * Checks whether the given userId matches with {@link #first#getId()}.
     * If it does not, {@link #first} is flipped with {@link #second}.
     *
     * @param userId the user id
     * @throws HttpException thrown in case the userId does not match with neither first nor second
     */
    public void checkUser(final UUID userId) throws HttpException {
        if (!first.getId().equals(userId)) {
            UserDto tmp = first;
            first = second;
            second = tmp;
        }
        if (!first.getId().equals(userId))
            throw new HttpException(HttpStatus.FORBIDDEN);
    }

}
