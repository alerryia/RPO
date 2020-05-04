import UIKit

class ViewController: UIViewController {
    
    lazy var game = Сoncentration(nuberOfCards: cardButtons.count / 2)
    
    var flipCount :Int = 0{
        didSet{
            flipCountLabel.text = "Flips \(flipCount)"
        }
    }
    var emojiChoices: Array<String> = ["♉️", "♊️", "♋️", "♌️", "♍️", "♎️", "♏️", "♐️", "♑️", "♒️", "♓️"]
    var emoji = Dictionary <Int, String>()
    
    @IBOutlet var cardButtons: [UIButton]!
    
    @IBOutlet weak var flipCountLabel: UILabel!
    
    @IBAction func touchCard(_ sender: UIButton) {
        
        
        let cardNumber = cardButtons.firstIndex(of: sender)!
        
        game.chooseCard(at: cardNumber)
        updateViewFromModel​()
       
        flipCount += 1

    }
    
    @IBAction func restartGame(_ sender: UIButton) {
        game = Сoncentration(nuberOfCards: cardButtons.count / 2)
        emoji = Dictionary <Int, String>()
        emojiChoices = ["♉️", "♊️", "♋️", "♌️", "♍️", "♎️", "♏️", "♐️", "♑️", "♒️", "♓️"]
        updateViewFromModel​()
        
        flipCount = 0
    }
    func updateViewFromModel​() {
        for index  in  cardButtons.indices{
            
            let button = cardButtons[index]
            let card = game.cards[index]
            
            if card.isFaceUp{
                button.setTitle(emoji (for:card), for: UIControl.State.normal)
                button.backgroundColor = #colorLiteral(red: 1, green: 1, blue: 1, alpha: 1)
               
                
            }else {
                button.setTitle("", for: UIControl.State.normal)
                button.backgroundColor = card.isMatched ? #colorLiteral(red: 0.5843137503, green: 0.8235294223, blue: 0.4196078479, alpha: 0) : #colorLiteral(red: 0.6679978967, green: 0.4751212597, blue: 0.2586010993, alpha: 1)
                
            }
        }
    }
    
    func emoji(for card: Card) -> String {
        if emoji[card.identifier] == nil && emojiChoices.count > 0 {
            let randomIndex = arc4random_uniform(UInt32(emojiChoices.count))
            emoji[card.identifier] = emojiChoices.remove(at: Int(randomIndex))
        }
        
        return emoji[card.identifier] ?? "?"
        
    }
    
    
    
}

