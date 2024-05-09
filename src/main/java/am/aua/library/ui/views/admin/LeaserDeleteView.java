package am.aua.library.ui.views.admin;

import am.aua.library.models.Leaser;
import am.aua.library.repositories.LeaserRepository;
import am.aua.library.ui.components.AbstractPage;

public class LeaserDeleteView extends AbstractPage {

    private Leaser leaser;
    private LeaserRepository leaserRepository;

    public LeaserDeleteView(Leaser leaser, LeaserRepository leaserRepository) {
        super("Leaser Delete");
        this.leaser = leaser;
        this.leaserRepository = leaserRepository;
    }

    @Override
    protected void setup() {

    }

    @Override
    protected void addComponents() {

    }
}