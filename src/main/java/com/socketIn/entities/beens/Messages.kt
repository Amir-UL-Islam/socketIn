package com.socketIn.entities.beens

import com.socketIn.entities.enums.CurrentStatus
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import lombok.ToString

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class Messages {
    var message: String? = null
    var sender: String? = null
    var receiver: String? = null
    var date: String? = null
    var status: CurrentStatus? = null
}