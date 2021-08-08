package com.jayaspiya.everestbooks.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayaspiya.everestbooks.R
import com.jayaspiya.everestbooks.adapter.BookAdapter
import com.jayaspiya.everestbooks.model.Book

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        val bookRecyclerView: RecyclerView = view.findViewById(R.id.bookRecyclerView)
        var bookList = ArrayList<Book>()
        val book1 = Book(1, "A Man with One of Those Faces", "Caimh Mcdonnell", "The first time somebody tried to kill him was an accident. The second time was deliberate. Now Paul Mulchrone finds himself on the run with nobody to turn to except a nurse who has read one-too-many crime novels and a renegade copper with a penchant for violence. Together they must solve one of the most notorious crimes in Irish history or else they’ll be history.", "https://silly-visvesvaraya-f21a81.netlify.app/assets/a-man-with-one-of-those-faces--front.png", 450)
        val book2 = Book(2, "Don Quixote", "Miguel De Cervantes", "Don Quixote has become so entranced reading tales of chivalry that he decides to turn knight errant himself. In the company of his faithful squire, Sancho Panza, these exploits blossom in all sorts of wonderful ways. While Quixote's fancy often leads him astray—he tilts at windmills, imagining them to be giants—Sancho acquires cunning and a certain sagacity. Sane madman and wise fool, they roam the world together-and together they have haunted readers' imaginations for nearly four hundred years.", "https://silly-visvesvaraya-f21a81.netlify.app/assets/don-quixote--front.png", 240)
        val book3 = Book(3, "Dracula", "Bram Stoker", "Dracula is a Gothic horror novel by Bram Stoker, published in 1897. As an epistolary novel, the narrative is related through letters, diary entries, and newspaper articles. It has no single protagonist, but opens with solicitor Jonathan Harker taking a business trip to stay at the castle of a Transylvanian noble, Count Dracula. Harker escapes the castle after discovering that Dracula is a vampire, and the Count moves to England and plagues the seaside town of Whitby. ", "https://silly-visvesvaraya-f21a81.netlify.app/assets/dracula--front.png", 640)
        val book4 = Book(4, "The Art of War", "Sun Tzu", "This Chinese treatise on war was written by Sun Tzu in the 6th century B.C. Each one of the 13 chapters is devoted to a different aspect of warfare, making it the definitive work on military strategies and tactics of its time. Studied by generals from Napoleon to Rommel, it is still one of the most influential works on the subject and is required reading in most military academies around the world.", "https://silly-visvesvaraya-f21a81.netlify.app/assets/the-art-of-war--front.png", 470)
        val book5 = Book(5, "Children Of Castle Rock", "Natasha Farrant", "When Alice Mistlethwaite is shipped off to boarding school in Scotland, it's nothing like she imagines. Run by the mysterious Major, there are no punishments, and the students are more likely to be taught about body painting or extreme survival than maths or English! Then Alice's dad goes missing, and she must run away to find him. Can she persuade her new friends to help? So begins an epic quest across wild Scottish highlands and islands, where the children will face danger and excitement at every turn.", "https://silly-visvesvaraya-f21a81.netlify.app/assets/the-children-of-castle-rock--front.png", 250)
        val book6 = Book(6, "To Kill a Mocking Bird", "Harper Lee", "To Kill a Mockingbird is a novel by the American author Harper Lee. It was published in 1960 and was instantly successful. In the United States, it is widely read in high schools and middle schools. To Kill a Mockingbird has become a classic of modern American literature, winning the Pulitzer Prize. The plot and characters are loosely based on Lee's observations of her family, her neighbors and an event that occurred near her hometown of Monroeville, Alabama, in 1936, when she was ten.", "https://silly-visvesvaraya-f21a81.netlify.app/assets/to-kill-mocking-bird--front.png", 550)
        bookList.add(book1)
        bookList.add(book2)
        bookList.add(book3)
        bookList.add(book4)
        bookList.add(book5)
        bookList.add(book6)
        var titleList = mutableListOf<String>()
        for(book in bookList){
            titleList.add(book.title.toString())
        }
        val adapter = BookAdapter(bookList, requireContext())
        bookRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bookRecyclerView.adapter = adapter
        return view
    }


}