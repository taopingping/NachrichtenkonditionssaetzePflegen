package view.general;

import java.awt.Dimension;

public interface ElementSetting {
	public Dimension buttonDimension = new Dimension(320, 28);
	public Dimension inputDimension = new Dimension(200, 30);
	// Konditionstypen PVMS: Fügen Sie neuen Konditionstyp mit
	// Einführungszeichen in den geschweiften Klammer ein("mit Komma getrennt").
	public String[] kschlPVMS = { "", "YCOO", "YCRM", "YCRP", "YDMS", "YIBC",
			"YIBL", "YIBM", "YIBN", "YIBS", "YIPL", "YPIA", "YPML", "YPMT",
			"YPRC", "YPRV", "YSBA", "YWWS" };
	// Konditionstypen Zentralsystem: Fügen Sie neuen Konditionstyp mit
	// Einführungszeichen in den geschweiften Klammer ein("mit Komma getrennt").
	public String[] kschl06 = { "", "YCDD", "YCOC", "YCPM", "YCSP", "YCST",
			"YEDA", "YFA8", "YFAH", "YFAS", "YFAV", "YFAW", "YFMS", "YFMS",
			"YFTF", "YGEW", "YIMM", "YIMP", "YIMR", "YIPA", "YIWA", "YKFZ",
			"YMSC", "YPS1", "YPS2", "YPS3", "YPS5", "YSAD" };
}
