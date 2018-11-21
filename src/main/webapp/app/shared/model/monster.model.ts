import { IBattle } from 'app/shared/model//battle.model';

export interface IMonster {
  id?: number;
  currentHP?: number;
  typeId?: number;
  battles?: IBattle[];
}

export const defaultValue: Readonly<IMonster> = {};
