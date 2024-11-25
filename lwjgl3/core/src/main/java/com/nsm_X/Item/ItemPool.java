package com.nsm_X.Item;

import com.badlogic.gdx.utils.Pool;

public class ItemPool extends Pool<NewItem> {

    @Override
    protected NewItem newObject() {
        // Crée un item basique qui sera configuré plus tard
        return new NewItem();
    }

    // Méthode pour récupérer un item du pool
    public NewItem obtainItem() {
        return obtain();  // Récupère un item du pool
    }

    // Méthode pour recycler un item, c'est-à-dire de le rendre au pool
    public void recycleItem(NewItem item) {
        // Réinitialise l'item avant de le remettre dans le pool
        item.reset();  // Assure-toi que l'item est réinitialisé
        free(item);  // Libère l'item pour qu'il soit réutilisé
    }

}
