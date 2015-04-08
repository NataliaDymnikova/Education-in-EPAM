package com.epam.natalia_dymnikova.newstry2;

import java.util.List;

/**
 * Created by Natalia_Dymnikova on 4/8/2015.
 */
public interface INews {
	/// Return list of information.
	public List<Information> getNewNews(int number, int begin);
}
