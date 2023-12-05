package xueluoanping.dtbayoublues.data.lang;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import xueluoanping.dtbayoublues.util.RegisterFinderUtil;

public class Lang_EN extends LangHelper {
    public Lang_EN(DataGenerator gen, ExistingFileHelper helper,String modid) {
        super(gen, helper,modid, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(modid, "Dynamic Trees for Bayou Blues");
        add(RegisterFinderUtil.getBlock("dtbayoublues:cypress_sapling"), "Cypress Sapling");
        add(RegisterFinderUtil.getBlock("dtbayoublues:cypress_branch"), "Cypress Tree");
        add(RegisterFinderUtil.getItem("dtbayoublues:cypress_seed"), "Cypress Sapling");
        addSpecie("cypress","Cypress");


    }
}
