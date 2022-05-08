package com.mooc.meetingfilm.backendfilm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.meetingfilm.apis.film.vo.DescribeFilmRespVO;
import com.mooc.meetingfilm.backendfilm.controller.vo.DescribeActorsRespVO;
import com.mooc.meetingfilm.backendfilm.controller.vo.DescribeFilmsRespVO;
import com.mooc.meetingfilm.backendfilm.controller.vo.FilmSavedReqVO;
import com.mooc.meetingfilm.backendfilm.dao.entity.MoocFilmActorT;
import com.mooc.meetingfilm.backendfilm.dao.entity.MoocFilmInfoT;
import com.mooc.meetingfilm.backendfilm.dao.entity.MoocFilmT;
import com.mooc.meetingfilm.backendfilm.dao.mapper.MoocActorTMapper;
import com.mooc.meetingfilm.backendfilm.dao.mapper.MoocFilmActorTMapper;
import com.mooc.meetingfilm.backendfilm.dao.mapper.MoocFilmInfoTMapper;
import com.mooc.meetingfilm.backendfilm.dao.mapper.MoocFilmTMapper;
import com.mooc.meetingfilm.exception.CommonServiceException;
import com.mooc.meetingfilm.util.ToolUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 影片模块实现层
 * @author wang
 * @create 2022-04-21
 */
@Service
public class FilmServiceImpl implements FilmServiceAPI{

    @Resource
    private MoocActorTMapper actorTMapper;

    @Resource
    private MoocFilmTMapper filmTMapper;

    @Resource
    private MoocFilmInfoTMapper filmInfoTMapper;

    @Resource
    private MoocFilmActorTMapper filmActorTMapper;

    /**
     * 演员查询列表
     * @param nowPage
     * @param pageSize
     * @return
     * @throws CommonServiceException
     */
    @Override
    public IPage<DescribeActorsRespVO> describeActors(int nowPage, int pageSize) throws CommonServiceException {
        // 查询演员列表
        return actorTMapper.describeActors(new Page<>(nowPage,pageSize));
    }

    /**
     * 影片列表查询
     * @param nowPage
     * @param pageSize
     * @return
     * @throws CommonServiceException
     */
    @Override
    public IPage<DescribeFilmsRespVO> describeFilms(int nowPage, int pageSize) throws CommonServiceException {
        return filmTMapper.describeFilms(new Page<>(nowPage,pageSize));
    }

    /**
     * 根据电影id查询影片
     * @param filmId
     * @return
     * @throws CommonServiceException
     */
    @Override
    public DescribeFilmRespVO describeFilmById(String filmId) throws CommonServiceException {
        return filmTMapper.descrbeFilmById(filmId);
    }

    /**
     * 保存电影信息：由于新增一个电影信息，需要存到多个表中，因此这些存表操作应该是原子操作，即要么都执行，要么都不执行
     * @param reqVO
     * @throws CommonServiceException
     */
    @Override
    //出现Exception异常，则会回滚
    @Transactional(rollbackFor = Exception.class)
    public void saveFilm(FilmSavedReqVO reqVO) throws CommonServiceException {
        try {
            //保存电影主表
            MoocFilmT film = new MoocFilmT();
            film.setFilmName(reqVO.getFilmName());
            film.setFilmType(ToolUtils.str2Int(reqVO.getFilmTypeId()));
            film.setImgAddress(reqVO.getMainImgAddress());
            film.setFilmScore(reqVO.getFilmScore());
            film.setFilmPresalenum(ToolUtils.str2Int(reqVO.getPreSaleNum()));
            film.setFilmBoxOffice(ToolUtils.str2Int(reqVO.getBoxOffice()));
            film.setFilmSource(ToolUtils.str2Int(reqVO.getFilmSourceId()));
            film.setFilmCats(reqVO.getFilmCatIds());
            film.setFilmArea(ToolUtils.str2Int(reqVO.getAreaId()));
            film.setFilmDate(ToolUtils.str2Int(reqVO.getDateId()));
            film.setFilmTime(ToolUtils.str2LocalDateTime(reqVO.getFilmTime()+" 00:00:00"));
            film.setFilmStatus(ToolUtils.str2Int(reqVO.getFilmStatus()));

            filmTMapper.insert(film);

            //保存到电影子表
            MoocFilmInfoT filmInfo = new MoocFilmInfoT();

            filmInfo.setFilmId(film.getUuid()+"");
            filmInfo.setFilmEnName(reqVO.getFilmEnName());
            filmInfo.setFilmScore(reqVO.getFilmScore());
            filmInfo.setFilmScoreNum(ToolUtils.str2Int(reqVO.getFilmScorers()));
            filmInfo.setFilmLength(ToolUtils.str2Int(reqVO.getFilmLength()));
            filmInfo.setBiography(reqVO.getBiography());
            filmInfo.setDirectorId(ToolUtils.str2Int(reqVO.getDirectorId()));
            filmInfo.setFilmImgs(reqVO.getFilmImgs());


            filmInfoTMapper.insert(filmInfo);

            //数据库中的actorId是以#分隔存的
            String[] actorId = reqVO.getActIds().split("#");
            String[] roleNames = reqVO.getRoleNames().split("#");

            if (actorId.length != roleNames.length){
                throw new CommonServiceException(500, "演员和角色名数量不匹配");
            }

            for(int i = 0; i < actorId.length; ++i){
                //保存演员映射表
                MoocFilmActorT filmActor = new MoocFilmActorT();

                filmActor.setFilmId(film.getUuid());
                filmActor.setActorId(ToolUtils.str2Int(actorId[i]));
                filmActor.setRoleName(roleNames[i]);

                filmActorTMapper.insert(filmActor);
            }
        } catch (Exception e) {
            throw new CommonServiceException(500, e.getMessage());
        }
    }
}
