package com.submerge.web.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.submerge.sub.object.config.SubInput;
import com.submerge.web.model.entity.MergeHisto;
import com.submerge.web.service.HistoService;

@Service("sbmHistoService")
@Transactional
public class SbmHistoService implements HistoService {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void traceMerge(SubInput one, SubInput two) {

		Date currDate = Calendar.getInstance().getTime();

		MergeHisto histoLineOne = createFomSubInput(one, currDate);
		MergeHisto histoLineTwo = createFomSubInput(two, currDate);

		int idLine = (int) this.sessionFactory.getCurrentSession().save(histoLineOne);

		histoLineOne.setFkHisto(idLine);
		histoLineTwo.setFkHisto(idLine);

		this.sessionFactory.getCurrentSession().saveOrUpdate(histoLineOne);
		this.sessionFactory.getCurrentSession().saveOrUpdate(histoLineTwo);
	}

	private static MergeHisto createFomSubInput(SubInput input, Date dateHisto) {

		MergeHisto histo = new MergeHisto();

		histo.setPrimaryColor(input.getFontconfig().getColor());
		histo.setAlignment(input.getAlignment());
		histo.setFontName(input.getFontconfig().getName());
		histo.setFontSize(input.getFontconfig().getSize());
		histo.setOutlineColor(input.getFontconfig().getOutlineColor());
		histo.setOutlineWidth(input.getFontconfig().getOutlineWidth());
		histo.setHistoDate(dateHisto);

		return histo;
	}
}