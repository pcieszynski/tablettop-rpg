import { IShop } from 'app/shared/model//shop.model';
import { ICharacter } from 'app/shared/model//character.model';

export interface IItem {
  id?: number;
  name?: string;
  effect?: string;
  price?: number;
  shops?: IShop[];
  characters?: ICharacter[];
}

export const defaultValue: Readonly<IItem> = {};
