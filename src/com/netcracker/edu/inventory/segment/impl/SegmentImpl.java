package com.netcracker.edu.inventory.segment.impl;

import com.netcracker.edu.inventory.model.*;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.segment.Segment;
import com.netcracker.edu.inventory.service.impl.ServiceImpl;

import java.util.*;


/**
 * Created by User on 10.01.2017.
 */
public class SegmentImpl implements Segment {

    TreeMap<Unique.PrimaryKey,Unique> segment = new TreeMap<Unique.PrimaryKey, Unique>();
    ServiceImpl service = new ServiceImpl();

    @Override
    public boolean add(Unique element) {
        if(element == null ||
                element instanceof Unique.PrimaryKey ||
                element instanceof Rack ||
                element.getPrimaryKey() == null || contain(element.getPrimaryKey())) {
            return false;
        }
        segment.put(element.getPrimaryKey(), service.getIndependentCopy(element));
        return true;
    }

    @Override
    public boolean set(Unique element) {
        if(element == null ||  element instanceof Unique.PrimaryKey || !contain(element.getPrimaryKey())) {
            return false;
        }
        segment.put(element.getPrimaryKey(), service.getIndependentCopy(element));
        return true;
    }

    @Override
    public Unique get(Unique.PrimaryKey pk) {
        return service.getIndependentCopy(segment.get(pk));
    }

    @Override
    public boolean put(Unique element) {
        if(element == null || element instanceof Rack || element.getPrimaryKey() == null) {
            return false;
        }
        if(!contain(element.getPrimaryKey())) {
            return add(element);
        } else {
            return set(element);
        }
    }

    @Override
    public boolean contain(Unique.PrimaryKey pk) {
        if(pk != null && segment.containsKey(pk))
            return true;
        return false;
    }

    /**
     *  В этом методе используются три цикла:
     *      1) первый - проходит по каждому элементу из отображения segmentCopy
     *      2) второй - ищет поля типа DevicePK или ConnectionPK в текущем элементе
     *      3) третий - если поле найдено из пункта 2, то выполняется поиск элемента в
     *                  segmentCopy на равнозначность ихних первичных ключей
     *  В итоге поля сегмента записываються в отдельную коллекцию
     * которая в последствии заменяет коллекцию с полями в текущем элементе
     */
    @Override
    public Collection<Unique> getGraph() {
        TreeMap<Unique.PrimaryKey,Unique> segmentCopy = getIndependentCopy(segment);

        // анализ каждого элемента
        for(Map.Entry firstKey : segmentCopy.entrySet()) {
            // хранение измененных полей текущего элемента
            List<FeelableEntity.Field> changedFields = new ArrayList<FeelableEntity.Field>();
            // получить элемент сегмента
            FeelableEntity segmentCopyElement = (FeelableEntity)firstKey.getValue();
            // получение всех полей этого элемента
            label: for(FeelableEntity.Field field : segmentCopyElement.getAllFieldsList()) {
                Object value = field.getValue();
                Class type = field.getType();
                // если поля является коллекцией
                if(type.equals(List.class) || type.equals(Set.class)) {
                    Collection collection = (type.equals(List.class)) ? new ArrayList() : new HashSet();

                    for(Object val : (Collection) value) {
                        FeelableEntity feelableEntity = getBinding(val, segmentCopy);
                        if(feelableEntity != null)
                            collection.add(feelableEntity);
                        else
                            collection.add(val);
                    }
                    changedFields.add(new FeelableEntity.Field(type, collection));
                    continue label;
                }
                // если поле является устройством или соединением
                if (type.equals(Device.class) || type.equals(Connection.class)) {
                    // ищем в сегменте если нету такого же элемента с таким же первичным ключом
                    FeelableEntity feelableEntity = getBinding(value, segmentCopy);
                    if(feelableEntity != null) {
                        changedFields.add(new FeelableEntity.Field(type, feelableEntity));
                        continue label;
                    }
                }
                // поле не является устройством или соединением
                changedFields.add(field);
            }
            // изменяем поля этого элемента
            segmentCopyElement.fillAllFields(changedFields);
        }

        return segmentCopy.values();
    }

    // создаю копию для каждого элемента
    private TreeMap<Unique.PrimaryKey, Unique> getIndependentCopy(TreeMap<Unique.PrimaryKey, Unique> map) {
        TreeMap<Unique.PrimaryKey,Unique> copy = new TreeMap<Unique.PrimaryKey, Unique>();
        for(Map.Entry entry : map.entrySet()) {
            copy.put((Unique.PrimaryKey) entry.getKey(), service.getIndependentCopy((Unique) entry.getValue()));
        }
        return copy;
    }

    private FeelableEntity getBinding(Object value, TreeMap<Unique.PrimaryKey, Unique> segmentCopy ) {
        for (Map.Entry secondKey : segmentCopy.entrySet()) {
            Object primary = secondKey.getKey();
            // если поле и элемент сегмента являются типа Connection или наооборот
            if ((value instanceof ConnectionPK && primary instanceof ConnectionPK)
                    || (value instanceof DevicePK && primary instanceof DevicePK)) {
                // если поле не null и оно равняется первичному ключу из сегмента
                if (value != null && value.equals(secondKey.getKey())) {
                    // достаем элемент из отображения
                    return  (FeelableEntity) secondKey.getValue();
                }
            }
        }
        return null;
    }
}
