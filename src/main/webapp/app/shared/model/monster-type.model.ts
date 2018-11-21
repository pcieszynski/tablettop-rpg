import { IMonster } from 'app/shared/model//monster.model';

export interface IMonsterType {
  id?: number;
  name?: string;
  strength?: number;
  agility?: number;
  constituition?: number;
  intelligence?: number;
  willpower?: number;
  charisma?: number;
  hitpoints?: number;
  attack?: string;
  defense?: number;
  monsters?: IMonster[];
}

export const defaultValue: Readonly<IMonsterType> = {};
