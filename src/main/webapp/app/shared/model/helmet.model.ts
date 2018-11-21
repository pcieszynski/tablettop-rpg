import { ICharacter } from 'app/shared/model//character.model';
import { IShop } from 'app/shared/model//shop.model';

export interface IHelmet {
  id?: number;
  name?: string;
  effect?: string;
  price?: number;
  defence?: number;
  part?: string;
  characters?: ICharacter[];
  shops?: IShop[];
}

export const defaultValue: Readonly<IHelmet> = {};
