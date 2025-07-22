package id.ryan.myportofolio

object PortofoliosData {
	private val portofolioTitles = arrayOf(
		"Bank App",
		"Bibit Mobile App",
		"Covid App",
		"E-commerce",
		"Indomie App",
		"Kaktus App",
		"Mining Pool",
		"Nike",
		"Omah",
		"Parabot App",
		"Paypas",
		"Toko Paijo",
		"Trip"
	)
	
	private val portofolioDescriotion = arrayOf(
		"Bank App - Save money for your future",
		"Verification Number in your design",
		"Information Covid19 Mobile App",
		"Buy now",
		"Instant Noodles Ordering App",
		"Nature Cactus in Plantpedia",
		"Get your pasive income",
		"Nike landing page",
		"Furniture landing page",
		"Buy your furniture here",
		"Easy pay",
		"Shopping now pay later",
		"Trip now"
	)
	
	private val portofolioImages = intArrayOf(
		R.drawable.bank,
		R.drawable.bibit,
		R.drawable.covid19,
		R.drawable.ecommerce,
		R.drawable.indomie,
		R.drawable.kaktus,
		R.drawable.mining,
		R.drawable.nike,
		R.drawable.omah,
		R.drawable.parabot,
		R.drawable.paypas,
		R.drawable.tokopaijo,
		R.drawable.trip
	)
	
	val listData: ArrayList<Portofolio>
		get() {
			val list = arrayListOf<Portofolio>()
			for (position in portofolioTitles.indices) {
				val portofolio = Portofolio()
				portofolio.title = portofolioTitles[position]
				portofolio.description = portofolioDescriotion[position]
				portofolio.photo = portofolioImages[position]
				list.add(portofolio)
			}
			return list
		}
}