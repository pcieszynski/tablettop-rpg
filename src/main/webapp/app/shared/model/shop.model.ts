import { IEvent } from 'app/shared/model//event.model';
import { IItem } from 'app/shared/model//item.model';
import { IHelmet } from 'app/shared/model//helmet.model';
import { IArmour } from 'app/shared/model//armour.model';
import { IBoots } from 'app/shared/model//boots.model';
import { IGloves } from 'app/shared/model//gloves.model';
import { ILegs } from 'app/shared/model//legs.model';
import { IRightHand } from 'app/shared/model//right-hand.model';
import { ILeftHand } from 'app/shared/model//left-hand.model';

export interface IShop {
  id?: number;
  name?: string;
  isLoot?: boolean;
  events?: IEvent[];
  items?: IItem[];
  helmets?: IHelmet[];
  armours?: IArmour[];
  boots?: IBoots[];
  gloves?: IGloves[];
  legs?: ILegs[];
  rightHands?: IRightHand[];
  leftHands?: ILeftHand[];
}

export const defaultValue: Readonly<IShop> = {
  isLoot: false
};
