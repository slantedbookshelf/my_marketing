package com.slantedbookshelf.marketing.api;

import com.slantedbookshelf.marketing.api.dto.RaffleAwardListResponseDTO;
import com.slantedbookshelf.marketing.api.dto.RaffleRequestDTO;
import com.slantedbookshelf.marketing.api.dto.RaffleResponseDTO;
import com.slantedbookshelf.marketing.api.response.Response;

import java.util.List;

public interface IRaffleService {
    Response<Boolean> strategyArmory(Long strategyId);

    Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListResponseDTO requestDTO);

    Response<RaffleResponseDTO> randomRaffle(RaffleRequestDTO requestDTO);
}
