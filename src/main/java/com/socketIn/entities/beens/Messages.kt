package com.socketIn.entities.beens

import lombok.Getter
import lombok.Setter
import lombok.ToString
import javax.validation.constraints.NotEmpty

@Getter
@Setter
@ToString
class Messages {
    var message: String? = null
    var receiver: String? = null
}