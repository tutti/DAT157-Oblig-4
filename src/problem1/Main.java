package problem1;

import no.patternsolutions.javann.Hopfield;

public class Main {
	
	private final static String[] EYE_COLOURS = {
		"Amber",
		"Blue",
		"Brown",
		"Grey",
		"Green",
		"Hazel",
		"Red",
		"Violet",
		"Black",
		"Missing",
		"Glowing"
	};
	
	private final static String[] HAIR_TYPES = {
		"Short",
		"Long",
		"Curly",
		"Wavy",
		"Afro",
		"Spiky",
		"Super Saiyan",
		"Bald",
		"Dreadlocks"
	};
	
	private final static String[] SEXES = {
		"Male",
		"Female",
		"Transgender",
		"Will not conform to the patriarchy, scum"
	};
	
	private final static String[] COUNTRIES = {
		"Afghanistan",
		"Albania",
		"Algeria",
		"Andorra",
		"Angola",
		"Antigua and Barbuda",
		"Argentina",
		"Armenia",
		"Aruba",
		"Australia",
		"Austria",
		"Azerbaijan",
		"Bahamas",
		"Bahrain",
		"Bangladesh",
		"Barbados",
		"Belarus",
		"Belgium",
		"Belize",
		"Benin",
		"Bhutan",
		"Bolivia",
		"Bosnia and Herzegovina",
		"Botswana",
		"Brazil",
		"Brunei ",
		"Bulgaria",
		"Burkina Faso",
		"Burma",
		"Burundi",
		"Cambodia",
		"Cameroon",
		"Canada",
		"Cabo Verde",
		"Central African Republic",
		"Chad",
		"Chile",
		"China",
		"Colombia",
		"Comoros",
		"Congo, Democratic Republic of the",
		"Congo, Republic of the",
		"Costa Rica",
		"Cote d'Ivoire",
		"Croatia",
		"Cuba",
		"Curacao",
		"Cyprus",
		"Czechia",
		"Denmark",
		"Djibouti",
		"Dominica",
		"Dominican Republic",
		"East Timor",
		"Ecuador",
		"Egypt",
		"El Salvador",
		"Equatorial Guinea",
		"Eritrea",
		"Estonia",
		"Ethiopia",
		"Fiji",
		"Finland",
		"France",
		"Gabon",
		"Gambia",
		"Georgia",
		"Germany",
		"Ghana",
		"Greece",
		"Grenada",
		"Guatemala",
		"Guinea",
		"Guinea-Bissau",
		"Guyana",
		"Haiti",
		"Holy See",
		"Honduras",
		"Hong Kong",
		"Hungary",
		"Iceland",
		"India",
		"Indonesia",
		"Iran",
		"Iraq",
		"Ireland",
		"Israel",
		"Italy",
		"Jamaica",
		"Japan",
		"Jordan",
		"Kazakhstan",
		"Kenya",
		"Kiribati",
		"Kosovo",
		"Kuwait",
		"Kyrgyzstan",
		"Laos",
		"Latvia",
		"Lebanon",
		"Lesotho",
		"Liberia",
		"Libya",
		"Liechtenstein",
		"Lithuania",
		"Luxembourg",
		"Macau",
		"Macedonia",
		"Madagascar",
		"Malawi",
		"Malaysia",
		"Maldives",
		"Mali",
		"Malta",
		"Marshall Islands",
		"Mauritania",
		"Mauritius",
		"Mexico",
		"Micronesia",
		"Moldova",
		"Monaco",
		"Mongolia",
		"Montenegro",
		"Morocco",
		"Mozambique",
		"Namibia",
		"Nauru",
		"Nepal",
		"Netherlands",
		"New Zealand",
		"Nicaragua",
		"Niger",
		"Nigeria",
		"North Korea",
		"Norway",
		"Oman",
		"Pakistan",
		"Palau",
		"Palestinian Territories",
		"Panama",
		"Papua New Guinea",
		"Paraguay",
		"Peru",
		"Philippines",
		"Poland",
		"Portugal",
		"Qatar",
		"Romania",
		"Russia",
		"Rwanda",
		"Saint Kitts and Nevis",
		"Saint Lucia",
		"Saint Vincent and the Grenadines",
		"Samoa ",
		"San Marino",
		"Sao Tome and Principe",
		"Saudi Arabia",
		"Senegal",
		"Serbia",
		"Seychelles",
		"Sierra Leone",
		"Singapore",
		"Sint Maarten",
		"Slovakia",
		"Slovenia",
		"Solomon Islands",
		"Somalia",
		"South Africa",
		"South Korea",
		"South Sudan",
		"Spain ",
		"Sri Lanka",
		"Sudan",
		"Suriname",
		"Swaziland ",
		"Sweden",
		"Switzerland",
		"Syria",
		"Taiwan",
		"Tajikistan",
		"Tanzania",
		"Thailand ",
		"Timor-Leste",
		"Togo",
		"Tonga",
		"Trinidad and Tobago",
		"Tunisia",
		"Turkey",
		"Turkmenistan",
		"Tuvalu",
		"Uganda",
		"Ukraine",
		"United Arab Emirates",
		"United Kingdom",
		"United States",
		"Uruguay",
		"Uzbekistan",
		"Vanuatu",
		"Venezuela",
		"Vietnam",
		"Yemen",
		"Zambia",
		"Zimbabwe"
	};
	
	private final static String[] NAMES = {
		"Anne Bortinatta",
		"Erna Solberg",
		"Donald Toupee",
		"Magic Mike Pence",
		"Jose Alvarez Trump",
		"Nigel Farage",
		"Pikachu",
		"French Frieza",
		"Hamburglar",
		"Mister Hopfield",
		"Noddy",
		"The Sandman",
		"That guy from the Matrix",
		"Jessie",
		"James",
		"Tingle"
	};
	
	private static String pad(String base, int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length - base.length(); ++i) {
			sb.append("0");
		}
		sb.append(base);
		return sb.toString();
	}
	
	private static boolean[] toBoolArray(String eyeColour, String hairType, int height, String sex, int age, String country, String name) {
		int eyeColourVal = -1;
		int hairTypeVal = -1;
		int sexVal = -1;
		int countryVal = -1;
		int nameVal = -1;
		for (int i = 0; i < EYE_COLOURS.length; ++i) {
			if (EYE_COLOURS[i].equals(eyeColour)) eyeColourVal = i;
		}
		for (int i = 0; i < HAIR_TYPES.length; ++i) {
			if (HAIR_TYPES[i].equals(hairType)) hairTypeVal = i;
		}
		for (int i = 0; i < SEXES.length; ++i) {
			if (SEXES[i].equals(sex)) sexVal = i;
		}
		for (int i = 0; i < COUNTRIES.length; ++i) {
			if (COUNTRIES[i].equals(country)) countryVal = i;
		}
		for (int i = 0; i < NAMES.length; ++i) {
			if (NAMES[i].equals(name)) nameVal = i;
		}
		if (eyeColourVal == -1 || hairTypeVal == -1 || sexVal == -1 || countryVal == -1 || nameVal == -1) {
			throw new RuntimeException("You dun fucked up yo");
		}
		return toBoolArray(eyeColourVal, hairTypeVal, height, sexVal, age, countryVal, nameVal);
	}
	
	private static boolean[] toBoolArray(int eyeColour, int hairType, int height, int sex, int age, int country, int name) {
		boolean[] arr = new boolean[EYE_COLOURS.length + HAIR_TYPES.length + SEXES.length + COUNTRIES.length + NAMES.length + 17];
		arr[eyeColour] = true;
		arr[hairType + EYE_COLOURS.length] = true;
		arr[sex + EYE_COLOURS.length + HAIR_TYPES.length + 9] = true;
		arr[country + EYE_COLOURS.length + HAIR_TYPES.length + SEXES.length + 17] = true;
		
		String heightVal = pad(Integer.toBinaryString(height), 9);
		String ageVal = pad(Integer.toBinaryString(age), 8);
		String nameVal = pad(Integer.toBinaryString(name), 4);
		
		for (int i = 0; i < heightVal.length(); ++i) {
			if (heightVal.charAt(i) == '1') {
				arr[i + 20] = true;
			}
		}
		for (int i = 0; i < ageVal.length(); ++i) {
			if (ageVal.charAt(i) == '1') {
				arr[i + 33] = true;
			}
		}
		for (int i = 0; i < nameVal.length(); ++i) {
			if (nameVal.charAt(i) == '1') {
				arr[i + 244] = true;
			}
		}
		
		return arr;
	}
	
	private static String getEyeColour(boolean[] output) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < EYE_COLOURS.length; ++i) {
			if (output[i]) {
				if (sb.length() != 0) sb.append(", ");
				sb.append(EYE_COLOURS[i]);
			}
		}
		return sb.toString();
	}
	
	private static String getHairType(boolean[] output) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < HAIR_TYPES.length; ++i) {
			if (output[i + EYE_COLOURS.length]) {
				if (sb.length() != 0) sb.append(", ");
				sb.append(HAIR_TYPES[i]);
			}
		}
		return sb.toString();
	}
	
	private static int getHeight(boolean[] output) {
		int height = 0;
		for (int i = 0; i < 9; ++i) {
			height *= 2;
			if (output[i + EYE_COLOURS.length + HAIR_TYPES.length]) {
				height += 1;
			}
		}
		return height;
	}
	
	private static String getSex(boolean[] output) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < SEXES.length; ++i) {
			if (output[i + EYE_COLOURS.length + HAIR_TYPES.length + 9]) {
				if (sb.length() != 0) sb.append(", ");
				sb.append(SEXES[i]);
			}
		}
		return sb.toString();
	}
	
	private static int getAge(boolean[] output) {
		int height = 0;
		for (int i = 0; i < 8; ++i) {
			height *= 2;
			if (output[i + EYE_COLOURS.length + HAIR_TYPES.length + SEXES.length + 9]) {
				height += 1;
			}
		}
		return height;
	}
	
	private static String getCountry(boolean[] output) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < COUNTRIES.length; ++i) {
			if (output[i + EYE_COLOURS.length + HAIR_TYPES.length + SEXES.length + 17]) {
				if (sb.length() != 0) sb.append(", ");
				sb.append(COUNTRIES[i]);
			}
		}
		return sb.toString();
	}
	
	private static String getName(boolean[] output) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < NAMES.length; ++i) {
			if (output[i + EYE_COLOURS.length + HAIR_TYPES.length + SEXES.length + COUNTRIES.length + 17]) {
				if (sb.length() != 0) sb.append(", ");
				sb.append(NAMES[i]);
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		//Hopfield hopfield = new Hopfield(EYE_COLOURS.length + HAIR_TYPES.length + SEXES.length + COUNTRIES.length + NAMES.length + 17);
		Hopfield hopfield = new Hopfield();
		hopfield.setIterations(10000);
		
		/*
		"Jessie",
		"James",
		*/
		
		boolean[] anne = toBoolArray("Glowing", "Afro", 154, "Female", 28, "North Korea", "Anne Bortinatta");
		boolean[] erna = toBoolArray("Amber", "Short", 200, "Female", 23, "Norway", "Erna Solberg");
		boolean[] donald = toBoolArray("Black", "Short", 187, "Male", 72, "United States", "Donald Toupee");
		boolean[] magicmike = toBoolArray("Brown", "Short", 178, "Male", 65, "United States", "Magic Mike Pence");
		boolean[] jose = toBoolArray("Hazel", "Wavy", 179, "Male", 31, "Mexico", "Jose Alvarez Trump");
		boolean[] nigel = toBoolArray("Grey", "Bald", 176, "Male", 69, "United Kingdom", "Nigel Farage");
		boolean[] pikachu = toBoolArray("Black", "Super Saiyan", 33, "Male", 7, "Palestinian Territories", "Pikachu");
		boolean[] frieza = toBoolArray("Violet", "Bald", 139, "Will not conform to the patriarchy, scum", 255, "France", "French Frieza");
		boolean[] hamburglar = toBoolArray("Black", "Long", 177, "Male", 33, "United States", "Hamburglar");
		boolean[] johnhopfield = toBoolArray("Green", "Short", 183, "Male", 83, "United States", "Mister Hopfield");
		boolean[] noddy = toBoolArray("Green", "Short", 4, "Male", 11, "Saint Vincent and the Grenadines", "Noddy");
		boolean[] sandman = toBoolArray("Black", "Long", 188, "Transgender", 255, "Congo, Democratic Republic of the", "The Sandman");
		boolean[] neo = toBoolArray("Missing", "Short", 176, "Male", 33, "United States", "That guy from the Matrix");
		boolean[] jessie = toBoolArray("Black", "Spiky", 168, "Female", 29, "Mauritania", "Jessie");
		boolean[] james = toBoolArray("Black", "Long", 183, "Male", 28, "Antigua and Barbuda", "James");
		boolean[] tingle = toBoolArray("Green", "Spiky", 136, "Male", 22, "Azerbaijan", "Tingle");
		
		boolean[][] nastyPeople = {
			anne, erna, donald, magicmike, jose, nigel, pikachu, frieza, hamburglar, johnhopfield,
			noddy, sandman, neo, jessie, james, tingle
		};
		
		hopfield.trainPatterns(nastyPeople);
		
		boolean[] test = hopfield.run(donald);

		System.out.println("Name: " + getName(test));
		System.out.println("Sex: " + getSex(test));
		System.out.println("Age: " + getAge(test) + " years");
		System.out.println("Country: " + getCountry(test));
		System.out.println("Eye colour: " + getEyeColour(test));
		System.out.println("Hair type: " + getHairType(test));
		System.out.println("Height: " + getHeight(test) + "cm");
		
	}

}
