package com.example.equity.positions.domain.repository;

import com.example.equity.positions.domain.entity.SharesTradeRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pyg
 */
@Repository
public interface PositionsOperationRepository extends JpaRepository<SharesTradeRecords,Long> {


}
