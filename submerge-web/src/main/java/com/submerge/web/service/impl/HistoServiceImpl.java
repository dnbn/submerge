package com.submerge.web.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.submerge.api.subtitle.config.SimpleSubConfig;
import com.submerge.web.model.entity.MergeHisto;
import com.submerge.web.pages.bean.model.UserSubConfigBean;
import com.submerge.web.service.HistoService;

@Service
@Transactional
public class HistoServiceImpl implements HistoService {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void trace(SimpleSubConfig one, SimpleSubConfig two, UserSubConfigBean config) {

		Date currDate = Calendar.getInstance().getTime();

		MergeHisto histoLineOne = createHistoLine(one, config, currDate);
		MergeHisto histoLineTwo = createHistoLine(two, config, currDate);

		histoLineOne.setFileName(config.getFirstSubtitle().getFileName());
		histoLineTwo.setFileName(config.getSecondSubtitle().getFileName());

		int idLine = (int) this.sessionFactory.getCurrentSession().save(histoLineOne);

		histoLineOne.setFkHisto(idLine);
		histoLineTwo.setFkHisto(idLine);

		this.sessionFactory.getCurrentSession().saveOrUpdate(histoLineOne);
		this.sessionFactory.getCurrentSession().saveOrUpdate(histoLineTwo);
	}

	private static MergeHisto createHistoLine(SimpleSubConfig input, UserSubConfigBean config, Date dateHisto) {

		MergeHisto histo = new MergeHisto();

		histo.setPrimaryColor(input.getFontconfig().getColor());
		histo.setAlignment(input.getAlignment());
		histo.setFontName(input.getFontconfig().getName());
		histo.setFontSize(input.getFontconfig().getSize());
		histo.setOutlineColor(input.getFontconfig().getOutlineColor());
		histo.setOutlineWidth(input.getFontconfig().getOutlineWidth());
		histo.setHistoDate(dateHisto);
		histo.setAdjustTimecodes(config.isAdjustTimecodes());
		histo.setAvoidSwitch(config.isAvoidSwitch());
		histo.setClean(config.isClean());
		histo.setOneLine(config.isOneLine());

		return histo;
	}
}