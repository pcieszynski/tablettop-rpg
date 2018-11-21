import { ISkill } from 'app/shared/model//skill.model';
import { IGame } from 'app/shared/model//game.model';
import { IStatus } from 'app/shared/model//status.model';
import { IItem } from 'app/shared/model//item.model';

export interface ICharacter {
  id?: number;
  name?: string;
  level?: number;
  experience?: number;
  maxHitpoints?: number;
  currentHitpoints?: number;
  gold?: number;
  strength?: number;
  agility?: number;
  constituition?: number;
  intelligence?: number;
  willpower?: number;
  charisma?: number;
  skills?: ISkill[];
  games?: IGame[];
  statuses?: IStatus[];
  items?: IItem[];
  professionId?: number;
  helmetId?: number;
  armourId?: number;
  bootsId?: number;
  glovesId?: number;
  legsId?: number;
  rightHandId?: number;
  leftHandId?: number;
}

export const defaultValue: Readonly<ICharacter> = {};
