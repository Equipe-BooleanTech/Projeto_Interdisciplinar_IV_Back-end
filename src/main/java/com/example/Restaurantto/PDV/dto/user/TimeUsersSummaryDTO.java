package com.example.Restaurantto.PDV.dto.user;


import java.util.List;

public record TimeUsersSummaryDTO(List<UserDTO> users,
                                  long total) {
}
