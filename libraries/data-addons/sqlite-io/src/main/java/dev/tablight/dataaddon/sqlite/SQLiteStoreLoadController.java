/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.tablight.dataaddon.sqlite;

import dev.tablight.dataaddon.storeload.DefaultStoreLoadController;

public class SQLiteStoreLoadController extends DefaultStoreLoadController {
	
	
	
	@Override
	public void store(Class<?> registrableType) {
		
		super.store(registrableType);
	}

	@Override
	public void load(Class<?> registrableType) {
		super.load(registrableType);
	}
}
