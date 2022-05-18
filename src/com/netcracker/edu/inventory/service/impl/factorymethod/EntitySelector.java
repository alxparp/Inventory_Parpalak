package com.netcracker.edu.inventory.service.impl.factorymethod;

import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.inventory.model.impl.*;

/**
 * Created by User on 22.01.2017.
 */
public class EntitySelector {

    public FeelableEntity getEntity(Class entityType) {
        FeelableEntity unique = null;
        if (entityType.equals(Battery.class)) {
            unique = new Battery();
        } else if (entityType.equals(Router.class)) {
            unique = new Router();
        } else if (entityType.equals(Switch.class)) {
            unique = new Switch();
        } else if (entityType.equals(WifiRouter.class)) {
            unique = new WifiRouter();
        } else if (entityType.equals(OpticFiber.class)) {
            unique = new OpticFiber();
        } else if (entityType.equals(TwistedPair.class)) {
            unique = new TwistedPair();
        } else if (entityType.equals(ThinCoaxial.class)) {
            unique = new ThinCoaxial();
        } else if (entityType.equals(Wireless.class)) {
            unique = new Wireless();
        }
        return unique;
    }

}
