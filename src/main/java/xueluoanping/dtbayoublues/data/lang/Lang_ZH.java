package xueluoanping.dtbayoublues.data.lang;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import xueluoanping.dtbayoublues.util.RegisterFinderUtil;


public class Lang_ZH extends LangHelper {
	public Lang_ZH(DataGenerator gen, ExistingFileHelper helper,String modid) {
		super(gen, helper,modid, "zh_cn");
	}


	@Override
	protected void addTranslations() {
		add(modid, "动态的树：长沼蓝调附属");
		add(RegisterFinderUtil.getBlock("dtbayoublues:cypress_sapling"), "丝柏树苗");
		add(RegisterFinderUtil.getBlock("dtbayoublues:cypress_branch"), "丝柏树");
		add(RegisterFinderUtil.getItem("dtbayoublues:cypress_seed"), "丝柏果");
		addSpecie("cypress","丝柏");



	}


}
