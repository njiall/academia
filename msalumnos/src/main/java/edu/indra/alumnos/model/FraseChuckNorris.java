package edu.indra.alumnos.model;

/*
 * {
    "categories": [],
    "created_at": "2020-01-05 13:42:27.496799",
    "icon_url": "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
    "id": "7MVcJYj2SJSxemSGfhjBYA",
    "updated_at": "2020-01-05 13:42:27.496799",
    "url": "https://api.chucknorris.io/jokes/7MVcJYj2SJSxemSGfhjBYA", //HATEOAS - HAL
    "value": "The actual definition of thunder is Chuck Norris coming to kill you."
}
 */

public class FraseChuckNorris {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public FraseChuckNorris(String value) {
		super();
		this.value = value;
	}

	public FraseChuckNorris() {
		super();
	}

	@Override
	public String toString() {
		return "FraseChuckNorris [value=" + value + "]";
	}
	
	
	
	
}
