import { IMonster } from 'app/shared/model//monster.model';

export interface IBattle {
  id?: number;
  monster?: string;
  monsterNumber?: number;
  monsters?: IMonster[];
  eventId?: number;
}

export const defaultValue: Readonly<IBattle> = {};
