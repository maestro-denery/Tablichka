package dev.tablight.test.registries;

import dev.tablight.common.base.registry.annotation.group.Controller;
import dev.tablight.common.base.registry.storeload.DefaultStoreLoadController;

@Controller("dummyGroup")
public class DummyController extends DefaultStoreLoadController {
	private static DummyController instance;
	public static DummyController getInstance() {
		if (instance == null) instance = new DummyController();
		return instance;
	}
}
